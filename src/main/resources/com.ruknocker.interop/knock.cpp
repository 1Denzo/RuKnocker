#include "knock.h"
#pragma comment(lib, "Ws2_32.lib")
#include <winsock2.h>

enum class PROTO : int {
    TCP = SOCK_STREAM,
    UDP = SOCK_DGRAM
};

sockaddr_in getsockaddr_in(const char* ip, unsigned short port);

JNIEXPORT void JNICALL Java_com_ruknocker_services_KnockerService_knock(
    JNIEnv* env, 
    jobject, 
    jstring ip_str, 
    jshort port, 
    jint protocol_int)
{
    const char* ip = env->GetStringUTFChars(ip_str, NULL);
    PROTO protocol = static_cast<PROTO>(protocol_int);
    try {
        WSADATA wsaData;
        int iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
        if (iResult != NO_ERROR) throw "WSASturtup ERROR";
        SOCKET sock = socket(AF_INET, static_cast<int>(protocol), 0);
        if (sock == INVALID_SOCKET) throw "INVALID_SOCKET";
        sockaddr_in clientaddr = getsockaddr_in(ip, static_cast<unsigned short>(port));
        if (protocol == PROTO::TCP) {
            u_long flags = 1;
            ioctlsocket(sock, FIONBIO, &flags);
            connect(sock, (SOCKADDR*)&clientaddr, sizeof(clientaddr));
        } 
        else if (protocol == PROTO::UDP)
            sendto(sock, "", 1, 0, (SOCKADDR*)&clientaddr, sizeof(clientaddr));
        else throw "PROTO_ERROR";

        iResult = closesocket(sock);
        if (iResult == SOCKET_ERROR) 
            throw "SOCKET_ERROR";
        WSACleanup();
    }
    catch (...) {
        WSACleanup();
    }
}

sockaddr_in getsockaddr_in(const char* ip, unsigned short port) {
    sockaddr_in clientaddr;
    clientaddr.sin_family = AF_INET;
    clientaddr.sin_addr.s_addr = inet_addr(ip);
    clientaddr.sin_port = htons(port);
    return clientaddr;
}