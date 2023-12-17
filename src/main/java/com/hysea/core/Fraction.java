package com.hysea.core;

public class Fraction {

    public static Fraction ZERO = new Fraction(0);

    //分子
    private double numerator;

    //分母
    private double denominator;

    public double getNumerator() {
        return numerator;
    }

    public void setNumerator(double numerator) {
        this.numerator = numerator;
    }

    public double getDenominator() {
        return denominator;
    }

    public void setDenominator(double denominator) {
        this.denominator = denominator;
    }

    public Fraction(double numerator, double denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(double numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "numerator=" + numerator +
                ", denominator=" + denominator +
                '}';
    }

    // 乘法
    public Fraction multiply(double d) {
        return new Fraction(this.numerator * d, this.denominator).simplify();
    }

    public Fraction multiply(Fraction f) {
        return new Fraction(this.numerator * f.numerator, this.denominator * f.denominator).simplify();
    }

    // 除法
    public Fraction divide(double d) {
        return new Fraction(this.numerator, this.denominator * d).simplify();
    }

    public Fraction divide(Fraction f) {
        return new Fraction(this.numerator * f.denominator, this.denominator * f.numerator).simplify();
    }

    // 加法
    public Fraction add(Fraction f) {
        double commonDenominator = findCommonDenominator(this.denominator, f.denominator);
        return new Fraction(
                (this.numerator * (commonDenominator / this.denominator)) +
                        (f.numerator * (commonDenominator / f.denominator)),
                commonDenominator
        ).simplify();
    }

    public Fraction add(double d) {
        Fraction fractionD = new Fraction(d);
        return this.add(fractionD);
    }

    // 减法
    public Fraction subtract(Fraction f) {
        double commonDenominator = findCommonDenominator(this.denominator, f.denominator);
        return new Fraction(
                (this.numerator * (commonDenominator / this.denominator)) -
                        (f.numerator * (commonDenominator / f.denominator)),
                commonDenominator
        ).simplify();
    }

    public Fraction subtract(double d) {
        Fraction fractionD = new Fraction(d);
        return this.subtract(fractionD);
    }

    // 化简分数
    public Fraction simplify() {
        double gcd = findGCD(this.numerator, this.denominator);

        if (gcd != 0) {
            this.numerator /= gcd;
            this.denominator /= gcd;
        }

        return this;
    }

    // 寻找两个数的最小公倍数
    private double findCommonDenominator(double a, double b) {
        return (a * b) / findGCD(a, b);
    }

    // 寻找两个数的最大公约数
    public static double findGCD(double a, double b) {
        // 使用欧几里得算法
        while (b != 0) {
            double temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a); // 返回最大公约数，取绝对值确保结果是非负数
    }

    public double getValue(){
        return this.numerator / this.denominator;
    }

}
