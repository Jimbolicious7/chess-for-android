package com.example.chess;

/**
 * Enum representing all possible states resulting from a move
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
 */
public enum MoveResult {
    Success, Illegal, Resign, Check, CheckMate, DrawOffered, DrawAccepted, None 
}