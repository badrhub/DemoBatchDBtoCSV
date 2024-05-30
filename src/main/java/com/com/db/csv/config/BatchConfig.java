package com.com.db.csv.config;

import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.com.db.csv.dto.ZeTransactionDTO;
import com.com.db.csv.model.ZeTransaction;
import com.com.db.csv.repository.ZeTransactionRepository;


@Configuration
public class BatchConfig {

		@Autowired
		private ZeTransactionRepository zeTransactionRepository;
	    
	    @Bean
	    RepositoryItemReader<ZeTransaction> reader(){
	        RepositoryItemReader<ZeTransaction> repositoryItemReader = new RepositoryItemReader<>();
	        repositoryItemReader.setRepository(zeTransactionRepository);
	        repositoryItemReader.setMethodName("findAll");
	        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
	        sorts.put("id", Sort.Direction.ASC);
	        repositoryItemReader.setSort(sorts);
	        return repositoryItemReader;
	    }
	  
	    @Bean
	    ItemProcessor<ZeTransaction, ZeTransactionDTO> processor(){ 
	        return new ZeTransactionItemProcessor(); 
	    } 
	  
	    
	    @Bean
	    ItemWriter<ZeTransactionDTO> csvItemWriter() {
	    	String[] tokens = new String[] { "numero_lot", "id_client", "nom_produit", "quantite", "date_transaction","montant" };
	        return new FlatFileItemWriterBuilder<ZeTransactionDTO>()
	                .name("csvItemWriter")
	                .resource(new FileSystemResource("transactions.csv"))
	                .lineAggregator(new DelimitedLineAggregator<ZeTransactionDTO>() { 
		                { 
		                    setDelimiter(";"); 
		                    setFieldExtractor(new BeanWrapperFieldExtractor<ZeTransactionDTO>() { 
		                            { 
		                                setNames(tokens); 
		                            } 
		                        }); 
		                } 
		            })
	                .headerCallback(new FlatFileHeaderCallback() {
	                	public void writeHeader(java.io.Writer writer) throws java.io.IOException {
	                		writer.write( "numero_lot ; id_client ; nom_produit ; quantite ; date_transaction ; montant" );
	                	};})
	                .build();
	    }
	    
		@Bean
		Job writerJob(JobRepository jobRepository, Step step) {
			return new JobBuilder("writerJob", jobRepository)
					.start(step)
					.listener(batchJobListener())
					.build();
		}

		@Bean
		Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
				RepositoryItemReader<ZeTransaction> reader, ItemProcessor<ZeTransaction, ZeTransactionDTO> processor,
				ItemWriter<ZeTransactionDTO> writer) {
			return new StepBuilder("step", jobRepository)
					.<ZeTransaction, ZeTransactionDTO>chunk(10, transactionManager)
					.reader(reader)
					.processor(processor)
					.writer(writer)
					.build();
		}
		
		@Bean 
		BatchJobListener batchJobListener() {
			return new BatchJobListener();
		}
		
		@Bean
		PlatformTransactionManager transactionManager() {
		    return new JpaTransactionManager();
		} 
		
	    
	}