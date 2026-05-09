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
Shared Pact constants for lance-namespace-python-urllib3 consumer tests.

Consumer name: lance-namespace-python-urllib3
Provider name: lance-namespace-server

Uses pact-python v3 API: each test creates its own Pact and uses
``pact.serve()`` as a context manager.  No session-scoped mock server.
"""
import os
from pathlib import Path

CONSUMER_NAME = "lance-namespace-python-urllib3"
PROVIDER_NAME = "lance-namespace-server"

# Directory where generated pact JSON files are written
PACT_DIR: Path = Path(os.path.dirname(__file__)) / "pacts"

# Ensure the output directory exists at import time
PACT_DIR.mkdir(parents=True, exist_ok=True)
