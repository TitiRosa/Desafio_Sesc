package base;

public class DadosUsuario {

    private String nomeCompleto;
    private String email;
    private String cpf;
    private String nomeUsuario;
    private String senha;
    private String empresa;
    private String uf;
    private String perfil;
    private String poloNome;
    private String poloValue;

   public DadosUsuario(String nomeCompleto, String email, String cpf, String nomeUsuario, String senha,
                        String empresa, String uf, String perfil, String poloNome, String poloValue) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cpf = cpf;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.empresa = empresa;
        this.uf = uf;
        this.perfil = perfil;
        this.poloNome = poloNome;
        this.poloValue = poloValue;
    }

   public DadosUsuario() {}

    // Getters
    public String getNomeCompleto() { return nomeCompleto; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getSenha() { return senha; }
    public String getEmpresa() { return empresa; }
    public String getUf() { return uf; }
    public String getPerfil() { return perfil; }
    public String getPoloNome() { return poloNome; }
    public String getPoloValue() { return poloValue; }

    //Setters
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public void setEmail(String email) { this.email = email; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public void setUf(String uf) { this.uf = uf; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public void setPoloNome(String poloNome) { this.poloNome = poloNome; }
    public void setPoloValue(String poloValue) { this.poloValue = poloValue; }
}
