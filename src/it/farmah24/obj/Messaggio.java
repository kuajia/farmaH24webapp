package it.farmah24.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONObject;

import it.farmah24.util.Util;

public class Messaggio {
	private int    id;
	private String titolo;
	private String messaggio;
	private String visibileDa;
	private String visibileA;
	private int    enabled;
	private String dataInvio;
	private String pubblicato;
	
	public Messaggio()
	{
		
	}
	
	public static Messaggio create(ResultSet rs) throws SQLException 
	{
		Messaggio bean = new Messaggio();
		bean.setId	 		( Util.getInt	(rs, "id"    	  ));
		bean.setTitolo      ( Util.getString(rs, "titolo"     ));
		bean.setMessaggio   ( Util.getString(rs, "messaggio"  ));
		bean.setVisibileDa  ( Util.getString(rs, "visibileDa" ));
		bean.setVisibileA   ( Util.getString(rs, "visibileA"  ));
		bean.setEnabled		( Util.getInt   (rs, "enabled"	  ));
		bean.setDataInvio	( Util.getString(rs, "dataInvio"  ));
		bean.setPubblicato	( Util.getString(rs, "pubblicato" ));
		return bean;	
	}	
	
	public static Messaggio fromJSON(JSONObject o)
	{		
		Messaggio bean = new Messaggio();

		bean.setId	 		( Util.getInt	(o , "id"    	 ));
		bean.setTitolo      ( Util.getString(o , "titolo"    ));
		bean.setMessaggio   ( Util.getString(o , "messaggio" ));
		bean.setVisibileDa  ( Util.getString(o , "visibileDa"));
		bean.setVisibileA   ( Util.getString(o , "visibileA" ));
		bean.setEnabled		( Util.getInt   (o , "enabled"	 ));
		bean.setDataInvio	( Util.getString(o , "dataInvio" ));

		return bean;
	}

	public int getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public String getVisibileDa() {
		return visibileDa;
	}

	public String getVisibileA() {
		return visibileA;
	}

	public int getEnabled() {
		return enabled;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public String getPubblicato() {
		return pubblicato;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public void setVisibileDa(String visibileDa) {
		this.visibileDa = visibileDa;
	}

	public void setVisibileA(String visibileA) {
		this.visibileA = visibileA;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public void setPubblicato(String pubblicato) {
		this.pubblicato = pubblicato;
	}
}
