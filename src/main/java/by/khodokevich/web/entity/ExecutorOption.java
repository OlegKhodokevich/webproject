package by.khodokevich.web.entity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExecutorOption extends Entity {
    private String unp;
    private List<Skill> skills;
    private double averageMark;
    private String description;
    private int numberContractsInProgress;
    private int numberCompletionContracts;
    private String urlPersonalFoto;

    public ExecutorOption() {
    }

    public ExecutorOption(String unp, String description, List<Skill> skills, double averageMark, int numberContractsInProgress, int numberCompletionContracts, String urlPersonalFoto) {
        this.unp = unp;
        this.description = description;
        this.skills = skills;
        this.averageMark = averageMark;
        this.numberContractsInProgress = numberContractsInProgress;
        this.numberCompletionContracts = numberCompletionContracts;
        this.urlPersonalFoto = urlPersonalFoto;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberContractsInProgress() {
        return numberContractsInProgress;
    }

    public void setNumberContractsInProgress(int numberContractsInProgress) {
        this.numberContractsInProgress = numberContractsInProgress;
    }

    public int getNumberCompletionContracts() {
        return numberCompletionContracts;
    }

    public void setNumberCompletionContracts(int numberCompletionContracts) {
        this.numberCompletionContracts = numberCompletionContracts;
    }

    public String getUrlPersonalFoto() {
        return urlPersonalFoto;
    }

    public void setUrlPersonalFoto(String urlPersonalFoto) {
        this.urlPersonalFoto = urlPersonalFoto;
    }

    @Override
    public boolean equals(Object o) {           //TODO change implementation
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutorOption that = (ExecutorOption) o;
        return Double.compare(that.averageMark, averageMark) == 0 && numberContractsInProgress == that.numberContractsInProgress && numberCompletionContracts == that.numberCompletionContracts && Objects.equals(unp, that.unp) && Objects.equals(skills, that.skills) && Objects.equals(urlPersonalFoto, that.urlPersonalFoto);
    }

    @Override
    public int hashCode() {                 //TODO change implementation
        return Objects.hash(unp, skills, averageMark, numberContractsInProgress, numberCompletionContracts, urlPersonalFoto);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append('{');
        sb.append("UNP= ").append(unp);
        sb.append(", description = ").append(description);
        sb.append(", skills= ").append(skills);
        sb.append(", averageMark= ").append(averageMark);
        sb.append(", numberContractsInProgress= ").append(numberContractsInProgress);
        sb.append(", numberCompletionContracts= ").append(numberCompletionContracts);
        sb.append(", urlPersonalFoto= ").append(urlPersonalFoto).append('}');
        return sb.toString();
    }
}
