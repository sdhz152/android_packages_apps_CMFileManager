/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.explorer.commands.shell;

/**
 * An interface for communicate shell results in a asynchronous way.
 */
public interface AsyncResultProgramListener {

    /**
     * Method invoked when the parse of results will start.
     */
    void onStartParsePartialResult();

    /**
     * Method invoked when the parse of results is ended and no new result.
     * will be received
     *
     * @param canceled Indicates if the program was canceled
     */
    void onEndParsePartialResult(boolean canceled);

    /**
     * Method invoked when a parse of new results are needed.
     *
     * @param partialIn A partial standard input buffer (incremental buffer)
     */
    void onParsePartialResult(String partialIn);

    /**
     * Method invoked when a parse of new error results are needed.
     *
     * @param partialErr A partial standard err buffer (incremental buffer)
     */
    void onParseErrorPartialResult(String partialErr);
}
