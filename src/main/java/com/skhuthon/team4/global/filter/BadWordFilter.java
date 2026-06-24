package com.skhuthon.team4.global.filter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadWordFilter {

    private static final List<String> BAD_WORDS = List.of(
            "욕설1", "욕설2", "욕설3",
            "씨발", "개새끼", "지랄", "병신", "미친놈", "꺼져",
            "죽어", "자살", "살인", "강간", "성폭행",
            "스팸", "광고", "홍보", "클릭", "구매"
    );

    public boolean containsBadWord(String content) {
        if (content == null) return false;
        String lower = content.toLowerCase();
        return BAD_WORDS.stream().anyMatch(lower::contains);
    }

    public String filter(String content) {
        if (content == null) return null;
        String result = content;
        for (String word : BAD_WORDS) {
            result = result.replace(word, "*".repeat(word.length()));
        }
        return result;
    }
}