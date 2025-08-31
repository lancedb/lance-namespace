#!/usr/bin/env python3
"""
Generate release notes for a given tag
"""

import argparse
import subprocess
import sys
from pathlib import Path
import requests
import json

def get_commits_since_last_tag(tag):
    """Get commits since the last tag"""
    # Get the previous tag
    try:
        result = subprocess.run(
            ["git", "describe", "--tags", "--abbrev=0", f"{tag}^"],
            capture_output=True,
            text=True,
            check=True
        )
        prev_tag = result.stdout.strip()
    except subprocess.CalledProcessError:
        # No previous tag, use all commits
        prev_tag = None
    
    # Get commits between tags
    if prev_tag:
        cmd = ["git", "log", f"{prev_tag}..{tag}", "--pretty=format:%H|%s|%an"]
    else:
        cmd = ["git", "log", tag, "--pretty=format:%H|%s|%an"]
    
    result = subprocess.run(cmd, capture_output=True, text=True, check=True)
    
    commits = []
    for line in result.stdout.strip().split('\n'):
        if line:
            parts = line.split('|', 2)
            if len(parts) == 3:
                commits.append({
                    'sha': parts[0],
                    'message': parts[1],
                    'author': parts[2]
                })
    
    return commits, prev_tag

def categorize_commits(commits):
    """Categorize commits by type"""
    categories = {
        'features': [],
        'fixes': [],
        'docs': [],
        'refactor': [],
        'test': [],
        'chore': [],
        'other': []
    }
    
    for commit in commits:
        msg = commit['message'].lower()
        if msg.startswith('feat:') or msg.startswith('feature:'):
            categories['features'].append(commit)
        elif msg.startswith('fix:'):
            categories['fixes'].append(commit)
        elif msg.startswith('docs:'):
            categories['docs'].append(commit)
        elif msg.startswith('refactor:'):
            categories['refactor'].append(commit)
        elif msg.startswith('test:'):
            categories['test'].append(commit)
        elif msg.startswith('chore:'):
            categories['chore'].append(commit)
        else:
            categories['other'].append(commit)
    
    return categories

def generate_markdown(tag, categories, prev_tag, repo):
    """Generate markdown release notes"""
    lines = []
    
    # Header
    lines.append(f"# Release {tag}")
    lines.append("")
    
    # Compare link
    if prev_tag and repo:
        lines.append(f"[Compare with {prev_tag}](https://github.com/{repo}/compare/{prev_tag}...{tag})")
        lines.append("")
    
    # Sections
    sections = [
        ('features', '✨ Features'),
        ('fixes', '🐛 Bug Fixes'),
        ('refactor', '♻️ Refactoring'),
        ('docs', '📚 Documentation'),
        ('test', '🧪 Tests'),
        ('chore', '🔧 Chores'),
        ('other', '📝 Other Changes')
    ]
    
    for key, title in sections:
        if categories[key]:
            lines.append(f"## {title}")
            lines.append("")
            for commit in categories[key]:
                msg = commit['message']
                # Remove conventional commit prefix
                for prefix in ['feat:', 'feature:', 'fix:', 'docs:', 'refactor:', 'test:', 'chore:']:
                    if msg.lower().startswith(prefix):
                        msg = msg[len(prefix):].strip()
                        break
                lines.append(f"- {msg} ({commit['sha'][:7]})")
            lines.append("")
    
    # Contributors section
    authors = set(commit['author'] for commit in sum(categories.values(), []))
    if authors:
        lines.append("## Contributors")
        lines.append("")
        for author in sorted(authors):
            lines.append(f"- {author}")
        lines.append("")
    
    return '\n'.join(lines)

def main():
    parser = argparse.ArgumentParser(description='Generate release notes')
    parser.add_argument('--tag', required=True, help='Git tag for the release')
    parser.add_argument('--repo', help='GitHub repository (owner/name)')
    parser.add_argument('--token', help='GitHub token for API access')
    parser.add_argument('--output', default='release_notes.md', help='Output file')
    
    args = parser.parse_args()
    
    try:
        # Get commits
        commits, prev_tag = get_commits_since_last_tag(args.tag)
        
        # Categorize commits
        categories = categorize_commits(commits)
        
        # Generate markdown
        markdown = generate_markdown(args.tag, categories, prev_tag, args.repo)
        
        # Write to file
        with open(args.output, 'w') as f:
            f.write(markdown)
        
        print(f"Release notes written to {args.output}")
        
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == '__main__':
    main()