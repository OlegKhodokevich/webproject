package by.khodokevich.web.model.entity;

import java.util.List;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutorOption executorOption)) return false;
        return Double.compare(executorOption.averageMark, averageMark) == 0 && numberContractsInProgress == executorOption.numberContractsInProgress
                && numberCompletionContracts == executorOption.numberCompletionContracts && unp.equals(executorOption.unp)
                && skills.containsAll(executorOption.skills) && executorOption.skills.containsAll(skills)
                && description.equalsIgnoreCase(executorOption.description) && urlPersonalFoto.equals(executorOption.urlPersonalFoto);
    }

    @Override
    public int hashCode() {
        int result = unp.hashCode() * 31 + skills.hashCode();
        result = result * 31 + (int) averageMark * 100;
        result = result * 31 + description.hashCode();
        result = result * 31 + numberCompletionContracts;
        result = result * 31 + numberContractsInProgress;
        result = result * 31 + urlPersonalFoto.hashCode();
        return result;
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
