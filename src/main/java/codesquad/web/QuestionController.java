package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

	
	@Resource(name = "qnaService")
	private QnaService qnaService;
	
	@GetMapping("/{id}")
	public String showDetail(@LoginUser User loginUser, Model model) {
		model.addAttribute("question", qnaService.findById(loginUser.getId()));
		return "/qna/show";
	}


}
