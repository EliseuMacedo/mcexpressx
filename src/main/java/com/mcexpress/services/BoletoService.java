package com.mcexpress.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.mcexpress.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido) {
		
		Calendar cal = Calendar.getInstance(); //vou criar um objeto e definir uma instancia de calendário java.util.Calendar
		cal.setTime(instanteDoPedido); //vou definir a data do calendario o parâmetro
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataVencimento(cal.getTime());
	}

}
