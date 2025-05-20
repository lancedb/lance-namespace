/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lancedb.lance.namespace.server;

import com.lancedb.lance.namespace.client.LanceNamespace;
import com.lancedb.lance.namespace.server.springboot.api.TableApi;
import com.lancedb.lance.namespace.server.springboot.model.GetTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.GetTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest;

import org.springframework.http.ResponseEntity;

public class TableController implements TableApi {

  private final LanceNamespace delegate;

  public TableController(LanceNamespace delegate) {
    this.delegate = delegate;
  }

  @Override
  public ResponseEntity<GetTableResponse> getTable(GetTableRequest getTableRequest) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseEntity<RegisterTableResponse> registerTable(
      RegisterTableRequest registerTableRequest) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseEntity<Object> tableExists(TableExistsRequest tableExistsRequest) {
    throw new UnsupportedOperationException();
  }
}
