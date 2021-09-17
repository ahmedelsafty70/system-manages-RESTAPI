package com.example.Phase12.service;

import com.example.Phase12.commands.vacation.addVacationCommand;
import com.example.Phase12.dto.addVacationDto;
import com.example.Phase12.exceptions.BadArgumentsException;
import com.example.Phase12.repository.VacationRepository;
import com.example.Phase12.sections.Vacation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VacationService {

    private VacationRepository vacationRepository;
    private ModelMapper modelMapper;

    public VacationService(VacationRepository vacationRepository, ModelMapper modelMapper) {
        this.vacationRepository = vacationRepository;
        this.modelMapper = modelMapper;
    }

    public Vacation mapToVacation(addVacationCommand vacationCommand){
        Vacation vacation = modelMapper.map(vacationCommand,Vacation.class);

        return vacation;
    }

    public addVacationDto savingVacation(addVacationCommand vacationCommand){

        Vacation vacation = mapToVacation(vacationCommand);

        Date d=new Date();
        int year=d.getYear();
        int currentYear=year+1900;


        int noOfVacation = vacationRepository.numberOfVacationDays(vacation.getEmployee().getIdEmployee(),vacation.getCurrentYear());

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
         Vacation vacationToBeDto = vacationRepository.save(vacation);

        addVacationDto vacationDto = new addVacationDto(vacationToBeDto.getId(),vacationToBeDto.getEmployee_name(),vacationToBeDto.getCurrentYear(),vacationToBeDto.getEmployee());

        return vacationDto;
    }

    public addVacationDto gettingVacation(int id){

         Optional<Vacation> vacation = vacationRepository.findById(id);
         addVacationDto vacationDto = new addVacationDto(vacation.get().getId(),vacation.get().getEmployee_name(),vacation.get().getCurrentYear(),vacation.get().getEmployee());

        return vacationDto;
    }

}