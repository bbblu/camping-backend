package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PredicateList implements Supplier<Predicate[]> {
    private final List<Predicate> predicateList = new ArrayList<>();

    public PredicateList add(Predicate predicate) {
        predicateList.add(predicate);
        return this;
    }

    public PredicateList add(Predicate... predicates) {
        predicateList.addAll(Arrays.asList(predicates));
        return this;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return predicateList.size();
    }

    @Override
    public Predicate[] get() {
        Predicate[] result = new Predicate[predicateList.size()];
        return predicateList.toArray(result);
    }
}
