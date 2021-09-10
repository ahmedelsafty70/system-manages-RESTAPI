package com.example.Phase12.service;

import com.example.Phase12.commands.addVacationCommand;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.Vacation;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VacationService {

    private VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public addVacationCommand savingVacation(addVacationCommand vacationCommand){

        Vacation vacation = new Vacation();

        vacation.setId(vacationCommand.getId());
        vacation.setEmployee_name(vacationCommand.getEmployee_name());
        vacation.setYear(vacationCommand.getYear());
        vacation.setEmployee(vacationCommand.getEmployee().get());
        vacation.setExceeded_day(vacationCommand.getExceeded_day());

        Date d=new Date();
        int year=d.getYear();
        int currentYear=year+1900;

        int noOfVacation = vacationRepository.numberOfVacationDays(vacation.getEmployee().getIdEmployee(),vacation.getYear());

        int yearsOfWorking = currentYear - vacation.getEmployee().getJoined_year();
        if(noOfVacation > 21 &&  yearsOfWorking < 10){
            vacation.setExceeded_day(1);
        }
        else if(noOfVacation > 30){
            vacation.setExceeded_day(1);
        }
        else{
            vacation.setExceeded_day(0);
        }
         vacationRepository.save(vacation);

        return vacationCommand;
    }

    public Optional<Vacation> gettingVacation(int id){ return vacationRepository.findById(id);}

}
