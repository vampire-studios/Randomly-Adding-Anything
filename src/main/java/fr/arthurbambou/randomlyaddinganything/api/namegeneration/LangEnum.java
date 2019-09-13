package fr.arthurbambou.randomlyaddinganything.api.namegeneration;

public enum LangEnum {
    en(new English());

    private INameGenerator nameGenerator;

    LangEnum(INameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public String generate() {
        return nameGenerator.generate();
    }
}
