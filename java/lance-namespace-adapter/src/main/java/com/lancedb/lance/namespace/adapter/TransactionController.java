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
package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.server.springboot.api.TransactionApi;
import com.lancedb.lance.namespace.server.springboot.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.server.springboot.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TransactionController implements TransactionApi {

  private final LanceNamespace delegate;

  public TransactionController(LanceNamespace delegate) {
    this.delegate = delegate;
  }

  @Override
  public ResponseEntity<DescribeTransactionResponse> describeTransaction(
      String id, DescribeTransactionRequest request, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.describeTransaction(
            delegate.describeTransaction(ServerToClientRequest.describeTransaction(request))));
  }

  @Override
  public ResponseEntity<AlterTransactionResponse> alterTransaction(
      String id, AlterTransactionRequest request, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.alterTransaction(
            delegate.alterTransaction(ServerToClientRequest.alterTransaction(request))));
  }
}
