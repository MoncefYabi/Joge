package joge.engine.math;

public class Dimension
{
	private int width;
	private int height;
	
	public Dimension()
	{
		this(0,0);
	}
	public Dimension(Dimension d)
	{
		this(d.getWidth(),d.getHeight());
	}
	public Dimension(int width,int height)
	{
		this.width=width;
		this.height=height;
	}
	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
}
