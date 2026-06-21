#!/usr/bin/env python3

from __future__ import annotations

import argparse
import tarfile
from pathlib import Path


def should_skip(path: Path, build_dir: str, dist_dir: str) -> bool:
    return any(part in {build_dir, dist_dir} for part in path.parts)


def create_archive(root: Path, output: Path, build_dir: str, dist_dir: str) -> None:
    output.parent.mkdir(parents=True, exist_ok=True)
    with tarfile.open(output, "w:gz") as archive:
        for path in root.rglob("*"):
            if path.is_dir():
                continue
            rel_path = path.relative_to(root)
            if should_skip(rel_path, build_dir, dist_dir):
                continue
            archive.add(path, arcname=rel_path.as_posix())


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--root", default=".", help="Directory to package")
    parser.add_argument("--output", required=True, help="Archive output path")
    parser.add_argument("--build-dir", default="build", help="Build directory name to exclude")
    parser.add_argument("--dist-dir", default="dist", help="Dist directory name to exclude")
    args = parser.parse_args()

    create_archive(Path(args.root).resolve(), Path(args.output), args.build_dir, args.dist_dir)


if __name__ == "__main__":
    main()
