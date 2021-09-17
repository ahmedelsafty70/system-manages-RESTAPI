package com.example.Phase12;

import org.modelmapper.ModelMapper;

public class ModelMapperGen
{

    static ModelMapper modelMapper = null;

    public static ModelMapper getModelMapperSingleton() {
        if(modelMapper == null){
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
        }
        return modelMapper;
    }
}