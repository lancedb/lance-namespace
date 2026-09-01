#!/usr/bin/env python3
"""Inject contract-test (WireMock + JUnit) configuration into the apache-client pom.xml.

The Apache Java client's pom.xml is produced by openapi-generator and is wiped on
every `make gen-apache-client` (since `clean-apache-client` does `rm -rf` on the
module).  We can't keep our test-only additions in the generated file directly, so
this script re-injects them after generation.

Specifically it adds:
  - <surefire ... <configuration><includes>**/*IT.java ...</includes></configuration>
so that surefire picks up integration-style WireMockIT.
  - junit-jupiter-engine + junit-platform-launcher test dependencies
    (the generator only declares jupiter-api).
    - wiremock-standalone test dependency (used by WireMockIT).

Idempotent: running the script twice produces the same result (it skips edits that
are already present).
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path

WIREMOCK_VERSION = "3.9.1"
# Apache client pins junit-jupiter to 5.8.2 (see the sed-based downgrade in
# java/Makefile).  junit-platform-launcher 1.8.2 is the matching platform
# release; keeping them aligned avoids classpath conflicts when surefire boots.
JUNIT_PLATFORM_LAUNCHER_VERSION = "1.8.2"

SUREFIRE_INCLUDES_BLOCK = """                    <includes>
                        <include>**/*Test.java</include>
                        <include>**/*Tests.java</include>
                        <include>**/Test*.java</include>
                        <include>**/*IT.java</include>
                    </includes>
"""

# The maven-surefire-plugin block in the generator-produced pom.xml ends with
# `<threadCount>10</threadCount>` followed by `</configuration>`.  We anchor on
# that line to reliably target the surefire <configuration> rather than any
# other plugin's <configuration> (e.g. maven-dependency-plugin's
# copy-dependencies execution).
SUREFIRE_ANCHOR_RE = re.compile(
    r"^(?P<indent>[ \t]*)<threadCount>10</threadCount>\s*\n"
    r"(?P<close>[ \t]*</configuration>\s*\n)",
    re.MULTILINE,
)

EXTRA_TEST_DEPS = f"""        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${{junit-version}}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-launcher</artifactId>
            <version>{JUNIT_PLATFORM_LAUNCHER_VERSION}</version>
            <scope>test</scope>
        </dependency>
        <!-- CTS: WireMock for contract testing -->
        <dependency>
            <groupId>org.wiremock</groupId>
            <artifactId>wiremock-standalone</artifactId>
            <version>{WIREMOCK_VERSION}</version>
            <scope>test</scope>
        </dependency>
"""


def inject_surefire_includes(pom: str) -> str:
    """Add an <includes> block (covering *IT.java) inside the maven-surefire <configuration>."""
    if "**/*IT.java" in pom:
        return pom

    def repl(match: re.Match[str]) -> str:
        return (
            f"{match.group('indent')}<threadCount>10</threadCount>\n"
            f"{SUREFIRE_INCLUDES_BLOCK}"
            f"{match.group('close')}"
        )

    new_pom, n = SUREFIRE_ANCHOR_RE.subn(repl, pom, count=1)
    if n != 1:
        raise SystemExit(
            "patch_apache_pom: failed to locate maven-surefire-plugin <configuration> "
            "block (anchored on <threadCount>10</threadCount>)"
        )
    return new_pom


def inject_extra_test_deps(pom: str) -> str:
    """Add junit-jupiter-engine, junit-platform-launcher and wiremock test dependencies.

    These are appended just before the closing </dependencies> tag.
    """
    if "wiremock-standalone" in pom:
        return pom
    marker = "    </dependencies>"
    if pom.count(marker) != 1:
        raise SystemExit(
            "patch_apache_pom: expected exactly one </dependencies> closing tag"
        )
    return pom.replace(marker, EXTRA_TEST_DEPS + marker, 1)


def patch(pom_path: Path) -> None:
    pom = pom_path.read_text(encoding="utf-8")
    patched = inject_surefire_includes(pom)
    patched = inject_extra_test_deps(patched)
    if patched != pom:
        pom_path.write_text(patched, encoding="utf-8")
        print(f"patched: {pom_path}")
    else:
        print(f"already up to date: {pom_path}")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "pom",
        type=Path,
        help="Path to the apache-client pom.xml emitted by openapi-generator",
    )
    args = parser.parse_args()
    if not args.pom.is_file():
        print(f"ERROR: pom file not found: {args.pom}", file=sys.stderr)
        return 1
    patch(args.pom)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
