#!/usr/bin/env python3
"""
Version management script for Lance Namespace project.
Updates version in multiple locations:
- Java: Makefiles and pom.xml files (excluding auto-generated ones)
- Python: Makefiles and pyproject.toml files (excluding auto-generated ones)
- Rust: Makefiles and Cargo.toml files (excluding auto-generated ones)
"""

import argparse
import subprocess
import sys
import re
from pathlib import Path
import toml
from lxml import etree

def update_makefile_version(makefile_path, new_version):
    """Update VERSION variable in Makefile"""
    content = makefile_path.read_text()
    updated_content = re.sub(
        r'^VERSION\s*=\s*.*$',
        f'VERSION = {new_version}',
        content,
        flags=re.MULTILINE
    )
    makefile_path.write_text(updated_content)
    print(f"Updated {makefile_path}")

def update_java_pom_version(pom_path, new_version):
    """Update version in pom.xml"""
    parser = etree.XMLParser(remove_blank_text=False, remove_comments=False)
    tree = etree.parse(str(pom_path), parser)
    root = tree.getroot()
    
    # Handle namespace
    nsmap = root.nsmap
    ns = nsmap[None] if None in nsmap else None
    
    # Create namespace prefix for XPath
    if ns:
        xpath_ns = {'m': ns}
        version_xpath = '//m:project/m:version'
        parent_version_xpath = '//m:project/m:parent/m:version'
    else:
        xpath_ns = None
        version_xpath = '//project/version'
        parent_version_xpath = '//project/parent/version'
    
    # Update project version
    version_elements = tree.xpath(version_xpath, namespaces=xpath_ns)
    for elem in version_elements:
        elem.text = new_version
    
    # Update parent version in root pom
    if 'pom.xml' in str(pom_path) and pom_path.parent.name == 'java':
        parent_version_elements = tree.xpath(parent_version_xpath, namespaces=xpath_ns)
        for elem in parent_version_elements:
            elem.text = new_version
    
    # Write back with proper formatting
    tree.write(str(pom_path), pretty_print=True, xml_declaration=True, encoding='UTF-8')
    print(f"Updated {pom_path}")

def update_python_pyproject_version(pyproject_path, new_version):
    """Update version in pyproject.toml"""
    data = toml.load(pyproject_path)
    
    # Update version in [project] section
    if 'project' in data and 'version' in data['project']:
        data['project']['version'] = new_version
    
    # Update version in [tool.poetry] section if exists
    if 'tool' in data and 'poetry' in data['tool'] and 'version' in data['tool']['poetry']:
        data['tool']['poetry']['version'] = new_version
    
    # Write back
    with open(pyproject_path, 'w') as f:
        toml.dump(data, f)
    print(f"Updated {pyproject_path}")

def update_rust_cargo_version(cargo_path, new_version):
    """Update version in Cargo.toml"""
    data = toml.load(cargo_path)
    
    # Update package version
    if 'package' in data and 'version' in data['package']:
        data['package']['version'] = new_version
    
    # Write back
    with open(cargo_path, 'w') as f:
        toml.dump(data, f)
    print(f"Updated {cargo_path}")

def main():
    parser = argparse.ArgumentParser(description='Bump version in Lance Namespace project')
    parser.add_argument('--version', required=True, help='New version to set')
    parser.add_argument('--dry-run', action='store_true', help='Show what would be changed without making changes')
    
    args = parser.parse_args()
    new_version = args.version
    
    print(f"Bumping version to: {new_version}")
    
    if args.dry_run:
        print("\nDry run mode - no changes will be made")
    
    # Update Makefiles
    makefiles = [
        Path('java/Makefile'),
        Path('python/Makefile'),
        Path('rust/Makefile')
    ]
    
    for makefile in makefiles:
        if makefile.exists():
            if not args.dry_run:
                update_makefile_version(makefile, new_version)
            else:
                print(f"Would update {makefile}")
    
    # Update Java pom.xml files (excluding auto-generated ones)
    java_poms = [
        Path('java/pom.xml'),
        Path('java/lance-namespace-core/pom.xml'),
        Path('java/lance-namespace-adapter/pom.xml'),
        Path('java/lance-namespace-hive2/pom.xml'),
        Path('java/lance-namespace-hive3/pom.xml'),
        Path('java/lance-namespace-glue/pom.xml'),
        Path('java/lance-namespace-unity/pom.xml'),
        Path('java/lance-namespace-lancedb/pom.xml')
    ]
    
    for pom in java_poms:
        if pom.exists():
            if not args.dry_run:
                update_java_pom_version(pom, new_version)
            else:
                print(f"Would update {pom}")
    
    # Update Python pyproject.toml files (excluding auto-generated ones)
    python_projects = [
        Path('python/lance_namespace/pyproject.toml'),
        Path('pyproject.toml')  # Root pyproject.toml if exists
    ]
    
    for pyproject in python_projects:
        if pyproject.exists():
            if not args.dry_run:
                update_python_pyproject_version(pyproject, new_version)
            else:
                print(f"Would update {pyproject}")
    
    # Update Rust Cargo.toml files (excluding auto-generated ones)
    rust_cargos = [
        Path('rust/Cargo.toml'),
        Path('rust/lance-namespace/Cargo.toml')
    ]
    
    for cargo in rust_cargos:
        if cargo.exists():
            if not args.dry_run:
                update_rust_cargo_version(cargo, new_version)
            else:
                print(f"Would update {cargo}")
    
    if not args.dry_run:
        print(f"\nSuccessfully updated version to {new_version}")
    else:
        print(f"\nDry run complete - would update version to {new_version}")

if __name__ == '__main__':
    main()