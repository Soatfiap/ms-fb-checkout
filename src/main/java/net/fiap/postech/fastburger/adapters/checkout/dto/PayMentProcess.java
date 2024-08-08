package net.fiap.postech.fastburger.adapters.checkout.dto;

public record PayMentProcess(String orderNumber, String type, boolean wasPayed) {
}
