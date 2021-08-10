package by.khodokevich.web.model.builder;

import by.khodokevich.web.model.entity.ExecutorOption;
import by.khodokevich.web.model.entity.Skill;

import java.util.List;

public class ExecutorOptionBuilder {
    private String unp;
    private String description;
    private List<Skill> skills;
    private double averageMark;
    private int numberContractsInProgress;
    private int numberCompletionContracts;
    private String urlPersonalFoto;

    public ExecutorOptionBuilder unp(String unp) {
        this.unp = unp;
        return this;
    }

    public ExecutorOptionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ExecutorOptionBuilder skills(List<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public ExecutorOptionBuilder averageMark(double averageMark) {
        this.averageMark = averageMark;
        return this;
    }

    public ExecutorOptionBuilder numberContractsInProgress(int numberContractsInProgress) {
        this.numberContractsInProgress = numberContractsInProgress;
        return this;
    }

    public ExecutorOptionBuilder numberCompletionContracts(int numberCompletionContracts) {
        this.numberCompletionContracts = numberCompletionContracts;
        return this;
    }

    public ExecutorOptionBuilder urlPersonalFoto(String urlPersonalFoto) {
        this.urlPersonalFoto = urlPersonalFoto;
        return this;
    }

    public ExecutorOption buildExecutorOption() {
        return new ExecutorOption(unp, description, skills, averageMark, numberContractsInProgress, numberCompletionContracts, urlPersonalFoto);
    }
}
