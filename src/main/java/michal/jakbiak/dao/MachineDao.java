package michal.jakbiak.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import michal.jakbiak.Machine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class MachineDao {
    private ObjectMapper objectMapper;
    private File machineFile;

    public List<Machine> load() {
        try {
            return objectMapper.readValue(Files.readString(machineFile.toPath()), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void save(List<Machine> machineList) {

        try {
            Files.writeString(machineFile.toPath(), objectMapper.writeValueAsString(machineList));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public MachineDao() throws IOException {

        machineFile = new File("./machine.txt");
        if (!machineFile.exists()) machineFile.createNewFile();

        this.objectMapper = new ObjectMapper();


    }

    public Optional<Machine> findOne (String machineName)
    {
        return load().stream().filter(m -> m.getName().equals(machineName)).findAny();
    }

}
