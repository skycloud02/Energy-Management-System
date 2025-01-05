package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getName(), device.getAddress(), device.getDescription(), device.getMaxEnergy(), device.getUserId());
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return new DeviceDetailsDTO(device.getId(), device.getName(), device.getAddress(), device.getDescription(), device.getMaxEnergy(), device.getUserId());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getName(),
                deviceDTO.getAddress(),
                deviceDTO.getDescription(),
                deviceDTO.getMaxEnergy(),
                deviceDTO.getUserId());
    }

    public static Device toEntityWithId(DeviceDetailsDTO deviceDetailsDTO) {
        return new Device(deviceDetailsDTO.getId(), deviceDetailsDTO.getName(), deviceDetailsDTO.getAddress(), deviceDetailsDTO.getDescription(), deviceDetailsDTO.getMaxEnergy(), deviceDetailsDTO.getUserId());
    }
}
