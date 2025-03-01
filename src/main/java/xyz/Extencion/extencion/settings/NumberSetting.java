package xyz.Extencion.extencion.settings;

public class NumberSetting extends Setting {
    private double value;
    private final double min;
    private final double max;
    private final double increment;

    public NumberSetting(String name, double defaultValue, double minimum, double maximum, double increment) {
        super(name);
        this.value = defaultValue;
        this.min = minimum;
        this.max = maximum;
        this.increment = increment;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    public void increment(boolean positive) {
        setValue(getValue() + (positive ? increment : -increment));
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getIncrement() {
        return increment;
    }
} 