package com.triquang.notifications.mapper;

import com.triquang.notifications.modal.Notification;
import com.triquang.notifications.payload.BookingDto;
import com.triquang.notifications.payload.NotificationDto;

public class NotificationMapper {

    public static NotificationDto toDto(Notification notification, BookingDto bookingDto) {
        if (notification == null) {
            return null;
        }

        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setType(notification.getType());
        dto.setDescription(notification.getDescription());
        dto.setIsRead(notification.getIsRead());
        dto.setUserId(notification.getUserId());
        dto.setSalonId(notification.getSalonId());
        dto.setBookingId(bookingDto.getId());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}
