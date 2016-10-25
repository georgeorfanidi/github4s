/*
 * Copyright (c) 2016 47 Degrees, LLC. <http://www.47deg.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package github4s

import scalaj.http._

object HttpClientExtensionJVM {

  implicit object ExtensionJVM extends HttpClientExtension[HttpResponse] {

    def run(rb: HttpRequestBuilder) = {

      val connTimeoutMs: Int = 1000
      val readTimeoutMs: Int = 5000

      val request = Http(rb.url)
        .method(rb.httpVerb.verb)
        .option(HttpOptions.connTimeout(connTimeoutMs))
        .option(HttpOptions.readTimeout(readTimeoutMs))
        .params(rb.params)
        .headers(rb.authHeader)
        .headers(rb.headers)

      rb.data match {
        case Some(d) ⇒ request.postData(d).header("content-type", "application/json").asString
        case _       ⇒ request.asString
      }
    }

  }

}
