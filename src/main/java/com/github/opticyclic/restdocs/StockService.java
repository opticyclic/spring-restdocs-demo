package com.github.opticyclic.restdocs;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class StockService {

  private final StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public List<Stock> findAll() {
    return stockRepository.findAll();
  }

  public Optional<Stock> findById(Long id) {
    return stockRepository.findById(id);
  }

  public Stock save(Stock stock) {
    return stockRepository.save(stock);
  }

  public void deleteById(Long id) {
    stockRepository.deleteById(id);
  }
}
