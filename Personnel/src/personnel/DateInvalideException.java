package personnel;

public class DateInvalideException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString()
	{
		return "Votre date est incorrecte!";
	}
}
