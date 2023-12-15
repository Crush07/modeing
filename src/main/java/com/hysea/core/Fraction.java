package com.hysea.core;

public class Fraction {

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

    //乘法
    public Fraction multiply(double d){
        this.numerator *= d;
        return this;
    }

    public Fraction multiply(Fraction f){
        this.denominator *= f.denominator;
        this.numerator *= f.numerator;
        return this;
    }

    //除法
    public Fraction divide(double d){
        this.denominator *= d;
        return this;
    }

    public Fraction divide(Fraction f){
        this.denominator *= f.numerator;
        this.numerator *= f.denominator;
        return this;
    }

    //加法
    public Fraction add(Fraction f) {
        double commonDenominator = findCommonDenominator(this.denominator, f.denominator);
        this.numerator = (this.numerator * (commonDenominator / this.denominator)) +
                (f.numerator * (commonDenominator / f.denominator));
        this.denominator = commonDenominator;
        return this;
    }

    public Fraction add(double d) {
        Fraction fractionD = new Fraction(d);
        return this.add(fractionD);
    }

    //减法
    public Fraction subtract(Fraction f) {
        double commonDenominator = findCommonDenominator(this.denominator, f.denominator);
        this.numerator = (this.numerator * (commonDenominator / this.denominator)) -
                (f.numerator * (commonDenominator / f.denominator));
        this.denominator = commonDenominator;
        return this;
    }

    public Fraction subtract(double d) {
        Fraction fractionD = new Fraction(d);
        return this.subtract(fractionD);
    }

    // 寻找两个数的最小公倍数
    private double findCommonDenominator(double a, double b) {
        return (a * b) / findGCD(a, b);
    }

    // 寻找两个数的最大公约数
    private double findGCD(double a, double b) {
        if (b == 0) {
            return a;
        } else {
            return findGCD(b, a % b);
        }
    }

    public double getValue(){
        return this.numerator / this.denominator;
    }

}
