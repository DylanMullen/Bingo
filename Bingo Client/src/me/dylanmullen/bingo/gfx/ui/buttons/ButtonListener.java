package me.dylanmullen.bingo.gfx.ui.buttons;

@FunctionalInterface
public interface ButtonListener
{
	Object placeholder();

	default void invoke()
	{

	}
}
