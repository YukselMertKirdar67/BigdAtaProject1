
package org.example.bigdataproject1;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Configuration
public class PlotConfig {

    @Value("classpath:plot.R")
    private Resource rSource;

    @Bean
    public Context getGraalVMContext() {
        return Context.newBuilder("R").allowAllAccess(true).build();
    }

    @Bean
    public Function<List<Double>, String> getPlotFunction(@Autowired Context ctx) throws IOException {
        Source source = Source.newBuilder("R", rSource.getURL()).build();
        return ctx.eval(source).as(Function.class);
    }
}