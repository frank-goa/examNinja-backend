package com.globalitgeeks.examninja.repository;

import com.globalitgeeks.examninja.model.Question;
import com.globalitgeeks.examninja.model.TestTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionRepositoryTest {

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByTestId_ShouldReturnPaginatedQuestions_WhenTestIdExists() {
        // Arrange
        Long testId = 1L;
        Pageable pageable = PageRequest.of(0, 10);  // First page with 10 questions
        List<Question> questions = Arrays.asList(
                new Question(1L, "Question 1", "A", "B", "C", "D", "E", "A", "Description", "Category", "Easy", 1),
                new Question(2L, "Question 2", "A", "B", "C", "D", "E", "B", "Description", "Category", "Easy", 2)
        );
        Page<Question> pagedQuestions = new PageImpl<>(questions, pageable, questions.size());
        when(questionRepository.findByTestId(testId, pageable)).thenReturn(pagedQuestions);

        // Act
        Page<Question> result = questionRepository.findByTestId(testId, pageable);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals("Question 1", result.getContent().get(0).getQuestion());
        assertEquals(testId, result.getContent().get(0).getTestId());
    }

    @Test
    void testFindByTestId_ShouldReturnEmptyPage_WhenTestIdDoesNotExist() {
        // Arrange
        Long testId = 999L;
        Pageable pageable = PageRequest.of(0, 10);
        when(questionRepository.findByTestId(testId, pageable)).thenReturn(Page.empty());

        // Act
        Page<Question> result = questionRepository.findByTestId(testId, pageable);

        // Assert
        assertEquals(0, result.getContent().size());
    }
}