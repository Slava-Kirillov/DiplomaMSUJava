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
 * <li>ComplexMy Specific Operations - Conjugate, Inverse, Absolute/Magnitude, Argument/Phase</li>
 * <li>Trigonometric Operations - sin, cos, tan, cot, sec, cosec</li>
 * <li>Mathematical Functions - exp</li>
 * <li>ComplexMy Parsing of type x+yi</li>
 * </ul>
 *
 * @author      Abdul Fatir
 * @version		1.2
 *
 */
public class ComplexMy
{
    /**
     * Used in <code>format(int)</code> to format the ComplexMy number as x+yi
     */
    public static final int XY = 0;
    /**
     * Used in <code>format(int)</code> to format the ComplexMy number as R.cis(theta), where theta is arg(z)
     */
    public static final int RCIS = 1;
    /**
     * The real, Re(z), part of the <code>ComplexMyNumber</code>.
     */
    private float real;
    /**
     * The imaginary, Im(z), part of the <code>ComplexMyNumber</code>.
     */
    private float imaginary;
    /**
     * Constructs a new <code>ComplexMyNumber</code> object with both real and imaginary parts 0 (z = 0 + 0i).
     */
    public ComplexMy()
    {
        real = 0.0f;
        imaginary = 0.0f;
    }

    /**
     * Constructs a new <code>ComplexMyNumber</code> object.
     * @param real the real part, Re(z), of the ComplexMy number
     * @param imaginary the imaginary part, Im(z), of the ComplexMy number
     */

    public ComplexMy(float real, float imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Adds another <code>ComplexMyNumber</code> to the current ComplexMy number.
     * @param z the ComplexMy number to be added to the current ComplexMy number
     */

    public void add(ComplexMy z)
    {
        set(add(this,z));
    }

    /**
     * Subtracts another <code>ComplexMyNumber</code> from the current ComplexMy number.
     * @param z the ComplexMy number to be subtracted from the current ComplexMy number
     */

    public void subtract(ComplexMy z)
    {
        set(subtract(this,z));
    }

    /**
     * Multiplies another <code>ComplexMyNumber</code> to the current ComplexMy number.
     * @param z the ComplexMy number to be multiplied to the current ComplexMy number
     */

    public void multiply(ComplexMy z)
    {
        set(multiply(this,z));
    }
    /**
     * Divides the current <code>ComplexMyNumber</code> by another <code>ComplexMyNumber</code>.
     * @param z the divisor
     */
    public void divide(ComplexMy z)
    {
        set(divide(this,z));
    }
    /**
     * Sets the value of current ComplexMy number to the passed ComplexMy number.
     * @param z the ComplexMy number
     */
    public void set(ComplexMy z)
    {
        this.real = z.real;
        this.imaginary = z.imaginary;
    }
    /**
     * Adds two <code>ComplexMyNumber</code>.
     * @param z1 the first <code>ComplexMyNumber</code>.
     * @param z2 the second <code>ComplexMyNumber</code>.
     * @return the resultant <code>ComplexMyNumber</code> (z1 + z2).
     */
    public static ComplexMy add(ComplexMy z1, ComplexMy z2)
    {
        return new ComplexMy(z1.real + z2.real, z1.imaginary + z2.imaginary);
    }

    /**
     * Subtracts one <code>ComplexMyNumber</code> from another.
     * @param z1 the first <code>ComplexMyNumber</code>.
     * @param z2 the second <code>ComplexMyNumber</code>.
     * @return the resultant <code>ComplexMyNumber</code> (z1 - z2).
     */
    public static ComplexMy subtract(ComplexMy z1, ComplexMy z2)
    {
        return new ComplexMy(z1.real - z2.real, z1.imaginary - z2.imaginary);
    }
    /**
     * Multiplies one <code>ComplexMyNumber</code> to another.
     * @param z1 the first <code>ComplexMyNumber</code>.
     * @param z2 the second <code>ComplexMyNumber</code>.
     * @return the resultant <code>ComplexMyNumber</code> (z1 * z2).
     */
    public static ComplexMy multiply(ComplexMy z1, ComplexMy z2)
    {
        float _real = z1.real*z2.real - z1.imaginary*z2.imaginary;
        float _imaginary = z1.real*z2.imaginary + z1.imaginary*z2.real;
        return new ComplexMy(_real,_imaginary);
    }

    public static ComplexMy multiply(float num, ComplexMy z){
        float _real = z.real*num;
        float _imaginary = z.imaginary*num;
        return new ComplexMy(_real, _imaginary);
    }
    /**
     * Divides one <code>ComplexMyNumber</code> by another.
     * @param z1 the first <code>ComplexMyNumber</code>.
     * @param z2 the second <code>ComplexMyNumber</code>.
     * @return the resultant <code>ComplexMyNumber</code> (z1 / z2).
     */
    public static ComplexMy divide(ComplexMy z1, ComplexMy z2)
    {
        ComplexMy output = multiply(z1,z2.conjugate());
        double div =  Math.pow(z2.mod(),2);

        double real = output.real/div;
        double image = output.imaginary/div;

        return new ComplexMy((float) real, (float) image);
    }

    /**
     * The ComplexMy conjugate of the current ComplexMy number.
     * @return a <code>ComplexMyNumber</code> object which is the conjugate of the current ComplexMy number
     */

    public ComplexMy conjugate()
    {
        return new ComplexMy(this.real,-this.imaginary);
    }

    /**
     * The modulus, magnitude or the absolute value of current ComplexMy number.
     * @return the magnitude or modulus of current ComplexMy number
     */

    public float mod()
    {
        return (float) Math.sqrt(Math.pow(this.real,2) + Math.pow(this.imaginary,2));
    }

    /**
     * The square of the current ComplexMy number.
     * @return a <code>ComplexMyNumber</code> which is the square of the current ComplexMy number.
     */

    public ComplexMy square()
    {
        float _real = this.real*this.real - this.imaginary*this.imaginary;
        float _imaginary = 2*this.real*this.imaginary;
        return new ComplexMy(_real,_imaginary);
    }
    /**
     * @return the ComplexMy number in x + yi format
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
     * Calculates the exponential of the <code>ComplexMyNumber</code>
     * @param z The input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is e^(input z)
     */
    public static ComplexMy exp(ComplexMy z)
    {
        float a = z.real;
        float b = z.imaginary;
        float r = (float) Math.exp(a);
        a = (float) (r*Math.cos(b));
        b = (float) (r*Math.sin(b));
        return new ComplexMy(a,b);
    }
    /**
     * Calculates the <code>ComplexMyNumber</code> to the passed integer power.
     * @param z The input ComplexMy number
     * @param power The power.
     * @return a <code>ComplexMyNumber</code> which is (z)^power
     */
    public static ComplexMy pow(ComplexMy z, int power)
    {
        ComplexMy output = new ComplexMy(z.getRe(),z.getIm());
        for(int i = 1; i < power; i++)
        {
            float _real = output.real*z.real - output.imaginary*z.imaginary;
            float _imaginary = output.real*z.imaginary + output.imaginary*z.real;
            output = new ComplexMy(_real,_imaginary);
        }
        return output;
    }
    /**
     * Calculates the sine of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the sine of z.
     */
    public static ComplexMy sin(ComplexMy z)
    {
        float x = (float) Math.exp(z.imaginary);
        float x_inv = 1/x;
        float r = (float) (Math.sin(z.real) * (x + x_inv)/2);
        float i = (float) (Math.cos(z.real) * (x - x_inv)/2);
        return new ComplexMy(r,i);
    }
    /**
     * Calculates the cosine of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the cosine of z.
     */
    public static ComplexMy cos(ComplexMy z)
    {
        float x = (float) Math.exp(z.imaginary);
        float x_inv = 1/x;
        float r = (float) (Math.cos(z.real) * (x + x_inv)/2);
        float i = (float) (-Math.sin(z.real) * (x - x_inv)/2);
        return new ComplexMy(r,i);
    }
    /**
     * Calculates the tangent of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the tangent of z.
     */
    public static ComplexMy tan(ComplexMy z)
    {
        return divide(sin(z),cos(z));
    }
    /**
     * Calculates the co-tangent of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the co-tangent of z.
     */
    public static ComplexMy cot(ComplexMy z)
    {
        return divide(new ComplexMy(1,0),tan(z));
    }
    /**
     * Calculates the secant of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the secant of z.
     */
    public static ComplexMy sec(ComplexMy z)
    {
        return divide(new ComplexMy(1,0),cos(z));
    }
    /**
     * Calculates the co-secant of the <code>ComplexMyNumber</code>
     * @param z the input ComplexMy number
     * @return a <code>ComplexMyNumber</code> which is the co-secant of z.
     */
    public static ComplexMy cosec(ComplexMy z)
    {
        return divide(new ComplexMy(1,0),sin(z));
    }
    /**
     * The real part of <code>ComplexMyNumber</code>
     * @return the real part of the ComplexMy number
     */
    public float getRe()
    {
        return this.real;
    }
    /**
     * The imaginary part of <code>ComplexMyNumber</code>
     * @return the imaginary part of the ComplexMy number
     */
    public float getIm()
    {
        return this.imaginary;
    }
    /**
     * The argument/phase of the current ComplexMy number.
     * @return arg(z) - the argument of current ComplexMy number
     */
    public float getArg()
    {
        return (float) Math.atan2(imaginary,real);
    }
    /**
     * Parses the <code>String</code> as a <code>ComplexMyNumber</code> of type x+yi.
     * @param s the input ComplexMy number as string
     * @return a <code>ComplexMyNumber</code> which is represented by the string.
     */
    public static ComplexMy parseComplexMy(String s)
    {
        s = s.replaceAll(" ","");
        ComplexMy parsed = null;
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
                parsed = new ComplexMy(Float.parseFloat(re),Float.parseFloat(im));
            }
            else if(s.lastIndexOf('-') > 0)
            {
                re = s.substring(0,s.lastIndexOf('-'));
                im = s.substring(s.lastIndexOf('-')+1,s.length());
                parsed = new ComplexMy(Float.parseFloat(re),-Float.parseFloat(im));
            }
        }
        else
        {
            // Pure imaginary number
            if(s.endsWith("i") || s.endsWith("I"))
            {
                s = s.replaceAll("i","");
                s = s.replaceAll("I","");
                parsed = new ComplexMy(0, Float.parseFloat(s));
            }
            // Pure real number
            else
            {
                parsed = new ComplexMy(Float.parseFloat(s),0);
            }
        }
        return parsed;
    }
    /**
     * Checks if the passed <code>ComplexMyNumber</code> is equal to the current.
     * @param z the ComplexMy number to be checked
     * @return true if they are equal, false otherwise
     */
    @Override
    public final boolean equals(Object z)
    {
        if (!(z instanceof ComplexMy))
            return false;
        ComplexMy a = (ComplexMy) z;
        return (real == a.real) && (imaginary == a.imaginary);
    }
    /**
     * The inverse/reciprocal of the ComplexMy number.
     * @return the reciprocal of current ComplexMy number.
     */
    public ComplexMy inverse()
    {
        return divide(new ComplexMy(1,0),this);
    }
    /**
     * Formats the ComplexMy number as x+yi or r.cis(theta)
     * @param format_id the format ID <code>ComplexMyNumber.XY</code> or <code>ComplexMyNumber.RCIS</code>.
     * @return a string representation of the ComplexMy number
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
            throw new IllegalArgumentException("Unknown ComplexMy Number format.");
        }
        return out;
    }
}
