package fr.epita.quiz_rest.models.datatransfer;

import java.util.Set;

public class QuestionToEvaluateDTO {
    private Integer parentQuestionId;
    private Set<Integer> selectedChoicesIds;

    public Integer getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(Integer parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public Set<Integer> getSelectedChoicesIds() {
        return selectedChoicesIds;
    }

    public void setSelectedChoicesIds(Set<Integer> selectedChoicesIds) {
        this.selectedChoicesIds = selectedChoicesIds;
    }
}
