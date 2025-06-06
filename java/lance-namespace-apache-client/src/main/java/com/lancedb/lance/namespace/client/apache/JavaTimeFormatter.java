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
package com.lancedb.lance.namespace.client.apache;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class that add parsing/formatting support for Java 8+ {@code OffsetDateTime} class. It's
 * generated for java clients when {@code AbstractJavaCodegen#dateLibrary} specified as {@code
 * java8}.
 */
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class JavaTimeFormatter {

  private DateTimeFormatter offsetDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  /**
   * Get the date format used to parse/format {@code OffsetDateTime} parameters.
   *
   * @return DateTimeFormatter
   */
  public DateTimeFormatter getOffsetDateTimeFormatter() {
    return offsetDateTimeFormatter;
  }

  /**
   * Set the date format used to parse/format {@code OffsetDateTime} parameters.
   *
   * @param offsetDateTimeFormatter {@code DateTimeFormatter}
   */
  public void setOffsetDateTimeFormatter(DateTimeFormatter offsetDateTimeFormatter) {
    this.offsetDateTimeFormatter = offsetDateTimeFormatter;
  }

  /**
   * Parse the given string into {@code OffsetDateTime} object.
   *
   * @param str String
   * @return {@code OffsetDateTime}
   */
  public OffsetDateTime parseOffsetDateTime(String str) {
    try {
      return OffsetDateTime.parse(str, offsetDateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * Format the given {@code OffsetDateTime} object into string.
   *
   * @param offsetDateTime {@code OffsetDateTime}
   * @return {@code OffsetDateTime} in string format
   */
  public String formatOffsetDateTime(OffsetDateTime offsetDateTime) {
    return offsetDateTimeFormatter.format(offsetDateTime);
  }
}
