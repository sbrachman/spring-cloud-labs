package demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.AdjectiveClient;
import demo.dao.ArticleClient;
import demo.dao.NounClient;
import demo.dao.SubjectClient;
import demo.dao.VerbClient;
import demo.domain.Word;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	VerbClient verbClient;

	@Autowired
	SubjectClient subjectClient;

	@Autowired
	ArticleClient articleClient;

	@Autowired
    AdjectiveClient adjectiveClient;

	@Autowired
    NounClient nounClient;
	
	
	@Override
    @HystrixCommand(fallbackMethod = "getFallbackSubject")
	public Word getSubject() {
		return subjectClient.getWord();
	}

	public Word getFallbackSubject(){
	    return new Word("Someone");
    }
	
	@Override
	public Word getVerb() {
		return verbClient.getWord();
	}
	
	@Override
	public Word getArticle() {
		return articleClient.getWord();
	}

	@Override
    @HystrixCommand(fallbackMethod = "getFallbackAdjective")
	public Word getAdjective() {
		return adjectiveClient.getWord();
	}

	public Word getFallbackAdjective() {
	    return new Word("");
    }
	
	@Override
    @HystrixCommand(fallbackMethod = "getFallbackNoun")
	public Word getNoun() {
		return nounClient.getWord();
	}

	public Word getFallbackNoun() {
	    return new Word("something");
    }
}
