package ru.diploma.data.complex;

/**
 * <code>ComplexNumber</code> is a class which implements complex numbers in Java.
 * It includes basic operations that can be performed on complex numbers such as,
 * addition, subtraction, multiplication, conjugate, modulus and squaring.
 * The data type for Complex Numbers.
 * <br /><br />
 * The features of this library include:<br />
 * <ul>
 * <li>Arithmetic Operations (addition, subtraction, multiplication, division)</li>
 * <li>Complex Specific Operations - Conjugate, Inverse, Absolute/Magnitude, Argument/Phase</li>
 * <li>Trigonometric Operations - sin, cos, tan, cot, sec, cosec</li>
 * <li>Mathematical Functions - exp</li>
 * <li>Complex Parsing of type x+yi</li>
 * </ul>
 *
 * @author      Abdul Fatir
 * @version		1.2
 *
 */
public class Complex
{
    /**
     * Used in <code>format(int)</code> to format the complex number as x+yi
     */
    public static final int XY = 0;
    /**
     * Used in <code>format(int)</code> to format the complex number as R.cis(theta), where theta is arg(z)
     */
    public static final int RCIS = 1;
    /**
     * The real, Re(z), part of the <code>ComplexNumber</code>.
     */
    private float real;
    /**
     * The imaginary, Im(z), part of the <code>ComplexNumber</code>.
     */
    private float imaginary;
    /**
     * Constructs a new <code>ComplexNumber</code> object with both real and imaginary parts 0 (z = 0 + 0i).
     */
    public Complex()
    {
        real = 0.0f;
        imaginary = 0.0f;
    }

    /**
     * Constructs a new <code>ComplexNumber</code> object.
     * @param real the real part, Re(z), of the complex number
     * @param imaginary the imaginary part, Im(z), of the complex number
     */

    public Complex(float real, float imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Adds another <code>ComplexNumber</code> to the current complex number.
     * @param z the complex number to be added to the current complex number
     */

    public void add(Complex z)
    {
        set(add(this,z));
    }

    /**
     * Subtracts another <code>ComplexNumber</code> from the current complex number.
     * @param z the complex number to be subtracted from the current complex number
     */

    public void subtract(Complex z)
    {
        set(subtract(this,z));
    }

    /**
     * Multiplies another <code>ComplexNumber</code> to the current complex number.
     * @param z the complex number to be multiplied to the current complex number
     */

    public void multiply(Complex z)
    {
        set(multiply(this,z));
    }
    /**
     * Divides the current <code>ComplexNumber</code> by another <code>ComplexNumber</code>.
     * @param z the divisor
     */
    public void divide(Complex z)
    {
        set(divide(this,z));
    }
    /**
     * Sets the value of current complex number to the passed complex number.
     * @param z the complex number
     */
    public void set(Complex z)
    {
        this.real = z.real;
        this.imaginary = z.imaginary;
    }
    /**
     * Adds two <code>ComplexNumber</code>.
     * @param z1 the first <code>ComplexNumber</code>.
     * @param z2 the second <code>ComplexNumber</code>.
     * @return the resultant <code>ComplexNumber</code> (z1 + z2).
     */
    public static Complex add(Complex z1, Complex z2)
    {
        return new Complex(z1.real + z2.real, z1.imaginary + z2.imaginary);
    }

    /**
     * Subtracts one <code>ComplexNumber</code> from another.
     * @param z1 the first <code>ComplexNumber</code>.
     * @param z2 the second <code>ComplexNumber</code>.
     * @return the resultant <code>ComplexNumber</code> (z1 - z2).
     */
    public static Complex subtract(Complex z1, Complex z2)
    {
        return new Complex(z1.real - z2.real, z1.imaginary - z2.imaginary);
    }
    /**
     * Multiplies one <code>ComplexNumber</code> to another.
     * @param z1 the first <code>ComplexNumber</code>.
     * @param z2 the second <code>ComplexNumber</code>.
     * @return the resultant <code>ComplexNumber</code> (z1 * z2).
     */
    public static Complex multiply(Complex z1, Complex z2)
    {
        float _real = z1.real*z2.real - z1.imaginary*z2.imaginary;
        float _imaginary = z1.real*z2.imaginary + z1.imaginary*z2.real;
        return new Complex(_real,_imaginary);
    }

    public static Complex multiply(float num, Complex z){
        float _real = z.real*num;
        float _imaginary = z.imaginary*num;
        return new Complex(_real, _imaginary);
    }
    /**
     * Divides one <code>ComplexNumber</code> by another.
     * @param z1 the first <code>ComplexNumber</code>.
     * @param z2 the second <code>ComplexNumber</code>.
     * @return the resultant <code>ComplexNumber</code> (z1 / z2).
     */
    public static Complex divide(Complex z1, Complex z2)
    {
        Complex output = multiply(z1,z2.conjugate());
        float div = (float) Math.pow(z2.mod(),2);
        return new Complex(output.real/div,output.imaginary/div);
    }

    /**
     * The complex conjugate of the current complex number.
     * @return a <code>ComplexNumber</code> object which is the conjugate of the current complex number
     */

    public Complex conjugate()
    {
        return new Complex(this.real,-this.imaginary);
    }

    /**
     * The modulus, magnitude or the absolute value of current complex number.
     * @return the magnitude or modulus of current complex number
     */

    public float mod()
    {
        return (float) Math.sqrt(Math.pow(this.real,2) + Math.pow(this.imaginary,2));
    }

    /**
     * The square of the current complex number.
     * @return a <code>ComplexNumber</code> which is the square of the current complex number.
     */

    public Complex square()
    {
        float _real = this.real*this.real - this.imaginary*this.imaginary;
        float _imaginary = 2*this.real*this.imaginary;
        return new Complex(_real,_imaginary);
    }
    /**
     * @return the complex number in x + yi format
     */
    @Override
    public String toString()
    {
        String re = this.real+"";
        String im = "";
        if(this.imaginary < 0)
            im = this.imaginary+"i";
        else
            im = "+"+this.imaginary+"i";
        return re+im;
    }

    /**
     * Calculates the exponential of the <code>ComplexNumber</code>
     * @param z The input complex number
     * @return a <code>ComplexNumber</code> which is e^(input z)
     */
    public static Complex exp(Complex z)
    {
        float a = z.real;
        float b = z.imaginary;
        float r = (float) Math.exp(a);
        a = (float) (r*Math.cos(b));
        b = (float) (r*Math.sin(b));
        return new Complex(a,b);
    }
    /**
     * Calculates the <code>ComplexNumber</code> to the passed integer power.
     * @param z The input complex number
     * @param power The power.
     * @return a <code>ComplexNumber</code> which is (z)^power
     */
    public static Complex pow(Complex z, int power)
    {
        Complex output = new Complex(z.getRe(),z.getIm());
        for(int i = 1; i < power; i++)
        {
            float _real = output.real*z.real - output.imaginary*z.imaginary;
            float _imaginary = output.real*z.imaginary + output.imaginary*z.real;
            output = new Complex(_real,_imaginary);
        }
        return output;
    }
    /**
     * Calculates the sine of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the sine of z.
     */
    public static Complex sin(Complex z)
    {
        float x = (float) Math.exp(z.imaginary);
        float x_inv = 1/x;
        float r = (float) (Math.sin(z.real) * (x + x_inv)/2);
        float i = (float) (Math.cos(z.real) * (x - x_inv)/2);
        return new Complex(r,i);
    }
    /**
     * Calculates the cosine of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the cosine of z.
     */
    public static Complex cos(Complex z)
    {
        float x = (float) Math.exp(z.imaginary);
        float x_inv = 1/x;
        float r = (float) (Math.cos(z.real) * (x + x_inv)/2);
        float i = (float) (-Math.sin(z.real) * (x - x_inv)/2);
        return new Complex(r,i);
    }
    /**
     * Calculates the tangent of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the tangent of z.
     */
    public static Complex tan(Complex z)
    {
        return divide(sin(z),cos(z));
    }
    /**
     * Calculates the co-tangent of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the co-tangent of z.
     */
    public static Complex cot(Complex z)
    {
        return divide(new Complex(1,0),tan(z));
    }
    /**
     * Calculates the secant of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the secant of z.
     */
    public static Complex sec(Complex z)
    {
        return divide(new Complex(1,0),cos(z));
    }
    /**
     * Calculates the co-secant of the <code>ComplexNumber</code>
     * @param z the input complex number
     * @return a <code>ComplexNumber</code> which is the co-secant of z.
     */
    public static Complex cosec(Complex z)
    {
        return divide(new Complex(1,0),sin(z));
    }
    /**
     * The real part of <code>ComplexNumber</code>
     * @return the real part of the complex number
     */
    public float getRe()
    {
        return this.real;
    }
    /**
     * The imaginary part of <code>ComplexNumber</code>
     * @return the imaginary part of the complex number
     */
    public float getIm()
    {
        return this.imaginary;
    }
    /**
     * The argument/phase of the current complex number.
     * @return arg(z) - the argument of current complex number
     */
    public float getArg()
    {
        return (float) Math.atan2(imaginary,real);
    }
    /**
     * Parses the <code>String</code> as a <code>ComplexNumber</code> of type x+yi.
     * @param s the input complex number as string
     * @return a <code>ComplexNumber</code> which is represented by the string.
     */
    public static Complex parseComplex(String s)
    {
        s = s.replaceAll(" ","");
        Complex parsed = null;
        if(s.contains(String.valueOf("+")) || (s.contains(String.valueOf("-")) && s.lastIndexOf('-') > 0))
        {
            String re = "";
            String im = "";
            s = s.replaceAll("i","");
            s = s.replaceAll("I","");
            if(s.indexOf('+') > 0)
            {
                re = s.substring(0,s.indexOf('+'));
                im = s.substring(s.indexOf('+')+1,s.length());
                parsed = new Complex(Float.parseFloat(re),Float.parseFloat(im));
            }
            else if(s.lastIndexOf('-') > 0)
            {
                re = s.substring(0,s.lastIndexOf('-'));
                im = s.substring(s.lastIndexOf('-')+1,s.length());
                parsed = new Complex(Float.parseFloat(re),-Float.parseFloat(im));
            }
        }
        else
        {
            // Pure imaginary number
            if(s.endsWith("i") || s.endsWith("I"))
            {
                s = s.replaceAll("i","");
                s = s.replaceAll("I","");
                parsed = new Complex(0, Float.parseFloat(s));
            }
            // Pure real number
            else
            {
                parsed = new Complex(Float.parseFloat(s),0);
            }
        }
        return parsed;
    }
    /**
     * Checks if the passed <code>ComplexNumber</code> is equal to the current.
     * @param z the complex number to be checked
     * @return true if they are equal, false otherwise
     */
    @Override
    public final boolean equals(Object z)
    {
        if (!(z instanceof Complex))
            return false;
        Complex a = (Complex) z;
        return (real == a.real) && (imaginary == a.imaginary);
    }
    /**
     * The inverse/reciprocal of the complex number.
     * @return the reciprocal of current complex number.
     */
    public Complex inverse()
    {
        return divide(new Complex(1,0),this);
    }
    /**
     * Formats the Complex number as x+yi or r.cis(theta)
     * @param format_id the format ID <code>ComplexNumber.XY</code> or <code>ComplexNumber.RCIS</code>.
     * @return a string representation of the complex number
     * @throws IllegalArgumentException if the format_id does not match.
     */
    public String format(int format_id) throws IllegalArgumentException
    {
        String out = "";
        if(format_id == XY)
            out = toString();
        else if(format_id == RCIS)
        {
            out = mod()+" cis("+getArg()+")";
        }
        else
        {
            throw new IllegalArgumentException("Unknown Complex Number format.");
        }
        return out;
    }
}
