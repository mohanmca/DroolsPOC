package com.nikias;

import com.nikias.model.Product;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class App {
    public static void main(String[] args) {

        KieSession ksession =    getKession();
        Product p = new Product();
        p.setType("diamond");
        FactHandle handle = ksession.insert(p);
        ksession.fireAllRules();
        System.out.println("The discount for the jewellery product "
                + p.getType() + " is " + p.getDiscount());

    }

    private static KieSession getKession() {
        KieServices ks = KieServices.get();
        KieContainer kcon =  ks.getKieClasspathContainer();
        System.out.println(kcon.getClassLoader() );
        System.out.println(kcon.getKieBaseNames());
        KieSession ksession = kcon.newKieSession("ksession-rule");
        return ksession;
    }
}
