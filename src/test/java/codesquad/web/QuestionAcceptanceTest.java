package codesquad.web;

import support.test.AcceptanceTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;

public class QuestionAcceptanceTest extends AcceptanceTest{
	private static final Logger log = LoggerFactory.getLogger(QuestionAcceptanceTest.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QnaService qnaService;

	@Test
	public void createList() throws Exception {
		Question question = new Question("초기 제목입니다", "초기 내용입니다.");
		qnaService.create(defaultUser(), question);
		
		ResponseEntity<String> response = template().getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(defaultUser().getName()), is(true));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void question_detail() throws Exception {
		Question question = new Question("제목입니다", "내용입니다.");
		qnaService.create(defaultUser(), question); //question 내용 저장
		
		ResponseEntity<String> response = basicAuthTemplate(defaultUser())
				.getForEntity(String.format("/questions/%d", defaultUser().getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(Long.toString(defaultUser().getId())), is(true));
	}
	
	@Test
	public void question_detail_fail() throws Exception {
		Question question = new Question("다른 사람이 넣은 제목입니다", "다른 사람이 넣은 내용입니다.");
		qnaService.create(defaultUser(), question); //question 내용 저장
		
		ResponseEntity<String> response = basicAuthTemplate(defaultUser())
				.getForEntity(String.format("/questions/%d", defaultUser().getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(defaultUser().getUserId().equals("sanjigi"), is(false));
	}
	
	@Test
	public void modify() throws Exception{
		
	}


}
