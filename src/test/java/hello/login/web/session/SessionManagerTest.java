package hello.login.web.session;

import hello.login.domain.member.Member;
import hello.login.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); //mySessionId=1231243-2314weqrw-1234sd

        //세션 조회
        Object result = sessionManager.getSession(request);

        assertThat(member).isEqualTo(result);

        //세션 만료
        sessionManager.expire(request);

        //만료 후 조회하면 없어야 한다.
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();

    }
}
