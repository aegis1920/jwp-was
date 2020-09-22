package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RequestHeaderFirstLineTest {

    @DisplayName("RequestHeaderFirstLine에서 http method 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, GET", "POST /index.html HTTP/1.1, POST"})
    void getMethod(String input, String expected) {
        RequestHeaderFirstLine requestHeaderFirstLine = new RequestHeaderFirstLine(input);
        assertThat(requestHeaderFirstLine.getMethod()).isEqualTo(expected);
    }

    @DisplayName("RequestHeaderFirstLine에서 get요청인지 확인")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, true", "POST /index.html HTTP/1.1, false"})
    void isGet(String input, boolean expected) {
        RequestHeaderFirstLine requestHeaderFirstLine = new RequestHeaderFirstLine(input);
        assertThat(requestHeaderFirstLine.isGet()).isEqualTo(expected);
    }

    @DisplayName("RequestHeaderFirstLine에서 post요청인지 확인")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, false", "POST /index.html HTTP/1.1, true"})
    void isPost(String input, boolean expected) {
        RequestHeaderFirstLine requestHeaderFirstLine = new RequestHeaderFirstLine(input);
        assertThat(requestHeaderFirstLine.isPost()).isEqualTo(expected);
    }

    @DisplayName("HttpHeader first line에서 리소스 경로 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, /index.html", "GET /index.html HTTP/1.1, /index.html"})
    void extractResourcePath(String input, String expected) {
        RequestHeaderFirstLine requestHeaderFirstLine = new RequestHeaderFirstLine(input);
        assertThat(requestHeaderFirstLine.getResourcePath()).isEqualTo(expected);
    }
}
