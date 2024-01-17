package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Стажер Java"));
        save(new Vacancy(0, "Junior Java Developer", "Джун Java"));
        save(new Vacancy(0, "Junior+ Java Developer", "Джун+ Java"));
        save(new Vacancy(0, "Middle Java Developer", "Мидл Java"));
        save(new Vacancy(0, "Middle+ Java Developer", "Мидл+ Java"));
        save(new Vacancy(0, "Senior Java Developer", "Сеньйор"));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public Vacancy deleteById(int id) {
        return vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCreationDate(),
                vacancy.getVisible(),
                vacancy.getCityId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
