package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.dto.Bank;
import tw.edu.ntub.imd.camping.service.BankService;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    private final TransactionUtils transactionUtils;

    public BankServiceImpl(TransactionUtils transactionUtils) {
        this.transactionUtils = transactionUtils;
    }

    @Override
    public List<Bank> searchAll() {
        return transactionUtils.searchBank();
    }
}
