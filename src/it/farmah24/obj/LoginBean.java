package it.farmah24.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.farmah24.util.Util;

@JsonInclude(Include.NON_NULL)
public class LoginBean 
{
	private int 	loginId;
	private String 	nome;
	private String 	cognome;
	private String 	ruolo;
	private int 	provinciaId;
	private String 	siglaProvincia;
	private String 	provincia;
	private String 	token;
	private String  email;
	private String  telefono;
	private int     farmaciaId;

	//----------------------------------------------------------------------------------------------
	
	public static LoginBean create(ResultSet rs) throws SQLException 
	{		
		LoginBean bean = new LoginBean();


		bean.setLoginId		  (Util.getInt   (rs, "p_loginId"		  ));
		bean.setNome    	  (Util.getString(rs, "@p_nome"  		  ));
		bean.setCognome		  (Util.getString(rs, "@p_cognome"  	  ));
		bean.setRuolo 		  (Util.getString(rs, "@p_ruolo"  		  ));
		bean.setProvinciaId	  (Util.getInt   (rs, "@p_provinciaId"	  ));
		bean.setSiglaProvincia(Util.getString(rs, "@p_siglaProvincia"));
		bean.setProvincia	  (Util.getString(rs, "@p_desProvincia"	  ));
		bean.setToken 		  (Util.getString(rs, "@p_token"  		  ));
		
		return bean;
	}
	
	//----------------------------------------------------------------------------------------------
	
	public static LoginBean create(int loginId, String token, String nome, String cognome, String ruolo, int provinciaId, String sigla, String desProv, int farmId, String email, String tel) throws SQLException 
	{		
		LoginBean bean = new LoginBean();


		bean.setLoginId		  (loginId    );
		bean.setNome    	  (nome       );
		bean.setCognome		  (cognome    );
		bean.setRuolo 		  (ruolo      );
		bean.setProvinciaId	  (provinciaId);
		bean.setSiglaProvincia(sigla      );
		bean.setProvincia	  (desProv    );
		bean.setToken 		  (token      );
		bean.setEmail		  (email      ); 		  
		bean.setTelefono      (tel        ); 	
		bean.setFarmaciaId    (farmId     );
		

		return bean;
	}

	public int getLoginId() {
		return loginId;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public int getProvinciaId() {
		return provinciaId;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefono() {
		return telefono;
	}

	public int getFarmaciaId() {
		return farmaciaId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public void setProvinciaId(int provinciaId) {
		this.provinciaId = provinciaId;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setFarmaciaId(int farmaciaId) {
		this.farmaciaId = farmaciaId;
	}

	@Override
	public String toString() {
		return "LoginBean [loginId=" + loginId + ", nome=" + nome + ", cognome=" + cognome + ", ruolo=" + ruolo
				+ ", provinciaId=" + provinciaId + ", siglaProvincia=" + siglaProvincia + ", provincia=" + provincia
				+ ", token=" + token + ", email=" + email + ", telefono=" + telefono + ", farmaciaId=" + farmaciaId
				+ "]";
	}
	
	//----------------------------------------------------------------------------------------------
	
}
