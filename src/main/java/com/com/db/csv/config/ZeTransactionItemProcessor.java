package com.com.db.csv.config;

import org.springframework.batch.item.ItemProcessor;

import com.com.db.csv.dto.ZeTransactionDTO;
import com.com.db.csv.model.ZeTransaction;

public class ZeTransactionItemProcessor implements ItemProcessor<ZeTransaction, ZeTransactionDTO> {

	@Override
	public ZeTransactionDTO process(ZeTransaction item) throws Exception {
		ZeTransactionDTO dto= new ZeTransactionDTO();
		
		dto.setDate_transaction(item.getDate_transaction());
		dto.setId_client(item.getId_client());
		dto.setMontant(item.getMontant());
		dto.setNom_produit(item.getNom_produit());
		dto.setNumero_lot(item.getNumero_lot());
		dto.setQuantite(item.getQuantite());
		return dto;
	}

}
