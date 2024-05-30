package com.com.db.csv.dto;

import java.time.LocalDate;

public class ZeTransactionDTO {
	
	private Long numero_lot;
	private String id_client;
	private String nom_produit;
	private Integer quantite;
	private Double montant;
	private LocalDate date_transaction;
	public Long getNumero_lot() {
		return numero_lot;
	}
	public void setNumero_lot(Long numero_lot) {
		this.numero_lot = numero_lot;
	}
	public String getId_client() {
		return id_client;
	}
	public void setId_client(String id_client) {
		this.id_client = id_client;
	}
	public String getNom_produit() {
		return nom_produit;
	}
	public void setNom_produit(String nom_produit) {
		this.nom_produit = nom_produit;
	}
	public Integer getQuantite() {
		return quantite;
	}
	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public LocalDate getDate_transaction() {
		return date_transaction;
	}
	public void setDate_transaction(LocalDate date_transaction) {
		this.date_transaction = date_transaction;
	}
	
	

}
