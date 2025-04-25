package edu.br.cruzeirodosul;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {
    private String nome;
    private String codigo;
    private String descricao;
    private String dataInicio;
    private String dataFim;

    // Construtor simplificado
    public Periodo(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = "";
        this.dataInicio = "";
        this.dataFim = "";
    }

    // Getters
    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    @Override
    public String toString() {
        return "Período: " + nome + " (Código: " + codigo + ")" +
               (descricao.isEmpty() ? "" : "\n  Descrição: " + descricao) +
               (dataInicio.isEmpty() ? "" : "\n  Data Início: " + dataInicio) +
               (dataFim.isEmpty() ? "" : "\n  Data Fim: " + dataFim);
    }
}
