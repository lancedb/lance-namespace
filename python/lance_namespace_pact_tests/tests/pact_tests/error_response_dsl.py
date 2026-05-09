# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""
ErrorResponse DSL helper for Python Pact consumer tests (pact-python v3).

Mirrors Java ErrorResponseDsl.java — all four fields (error, code, type, detail)
use type-matchers so only field types matter, not the exact values.
Per plan §8, the ErrorResponse has exactly these 4 fields.
"""
from pact import match


def error_response_body(
    error: str,
    code: int,
    error_type: str,
    detail: str = "An error occurred",
) -> dict:
    """Build a type-matched ErrorResponse body for Pact v3 interactions.

    Args:
        error: Error code string (e.g. ``"NAMESPACE_NOT_FOUND"``).
        code: HTTP status integer (e.g. ``404``).
        error_type: Exception class FQCN (e.g. ``"org.lance.namespace.NamespaceNotFoundException"``).
        detail: Human-readable detail message (used as example value only).

    Returns:
        Dictionary of pact-python v3 matchers representing the ErrorResponse shape.
    """
    return {
        "error": match.like(error),
        "code": match.integer(code),
        "type": match.like(error_type),
        "detail": match.like(detail),
    }
