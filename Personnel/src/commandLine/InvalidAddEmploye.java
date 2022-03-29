package commandLine;

public class InvalidAddEmploye extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString()
	{
		return "Impossible d'ajouter employe!";
	}
}
