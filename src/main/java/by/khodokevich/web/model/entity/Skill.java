package by.khodokevich.web.model.entity;


/**
 * Skill is entity outlining particular traits of executor.
 * This is what executor can do.
 *
 * @author Oleg Khodokevich
 */
public class Skill extends Entity {
    private Specialization specialization;
    private String cost;
    private UnitMeasure measure;

    public Skill() {
    }

    public Skill(Specialization specialization, String cost, UnitMeasure measure) {
        this.specialization = specialization;
        this.cost = cost;
        this.measure = measure;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public UnitMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(UnitMeasure measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill checkedSkill)) return false;
        return specialization == checkedSkill.specialization && checkedSkill.cost != null ? cost.equals(checkedSkill.cost) : checkedSkill.cost == cost
                && measure == checkedSkill.measure;
    }

    @Override
    public int hashCode() {
        int result = specialization != null ? specialization.ordinal() : 0;
        result = result * 31 + (cost != null ? cost.hashCode() : 0);
        result = result * 31 + (measure != null ? measure.ordinal() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append('{');
        sb.append("specialization=").append(specialization);
        sb.append(", cost= ").append(cost);
        sb.append(", measure=").append(measure.name().toLowerCase()).append('}');
        return sb.toString();
    }
}
