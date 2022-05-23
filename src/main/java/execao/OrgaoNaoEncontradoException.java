package execao;

public class OrgaoNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	private int codigo;
	
	public OrgaoNaoEncontradoException(String msg)
	{	super(msg);
	}

	public OrgaoNaoEncontradoException(int codigo, String msg)
	{	super(msg);
		this.codigo = codigo;
	}
	
	public int getCodigoDeErro()
	{	return codigo;
	}
}	